package com.charan.erp1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import com.charan.erp1.CONTROLLER.StudentController;
import com.charan.erp1.DTO.StudentRequest;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.EXCEPTION.GlobalExceptionHandler;
import com.charan.erp1.REPOSITORY.StudentRepository;
import com.charan.erp1.SERVICES.AuthTokenService;

@SpringBootTest
@ActiveProfiles("test")
class Erp1ApplicationTests {
	@Autowired
	private StudentController studentController;

	private MockMvc mockMvc;

	@Autowired
	private FilterChainProxy securityFilterChain;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private Validator validator;

	@Autowired
	private jakarta.validation.Validator beanValidator;

	@Autowired
	private AuthTokenService authTokenService;

	@BeforeEach
	void clearStudents() {
		mockMvc = MockMvcBuilders.standaloneSetup(studentController)
				.setControllerAdvice(new GlobalExceptionHandler())
				.setValidator(validator)
				.build();
		studentRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void issuesAndRotatesJwtTokensAndRejectsRefreshTokenReplay() {
		var firstPair = authTokenService.login("test-admin", "test-password-123");
		assertNotNull(firstPair.accessToken());
		assertNotNull(firstPair.refreshToken());

		var rotatedPair = authTokenService.refresh(firstPair.refreshToken());
		assertNotEquals(firstPair.refreshToken(), rotatedPair.refreshToken());
		var replayException = assertThrows(BadCredentialsException.class,
				() -> authTokenService.refresh(firstPair.refreshToken()));
		assertTrue(replayException.getMessage().contains("already used"));
	}

	@Test
	void studentEndpointsRequireAnAccessToken() throws Exception {
		MockMvc securedMockMvc = MockMvcBuilders.standaloneSetup(studentController)
				.addFilters(securityFilterChain)
				.setControllerAdvice(new GlobalExceptionHandler())
				.setValidator(validator)
				.build();

		securedMockMvc.perform(get("/api/students/99999"))
				.andExpect(status().isUnauthorized());

		var tokenPair = authTokenService.login("test-admin", "test-password-123");
		securedMockMvc.perform(get("/api/students/99999")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenPair.refreshToken()))
				.andExpect(status().isUnauthorized());

		securedMockMvc.perform(get("/api/students/99999")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenPair.accessToken()))
				.andExpect(status().isNotFound());
	}

	@Test
	void listsStudentsAsAJsonArrayAndFiltersByDepartment() throws Exception {
		Student student = new Student();
		student.setName("Ada Lovelace");
		student.setEmail("ada@example.edu");
		student.setPhone("555-0110");
		student.setRollNo("2024CS101");
		student.setDepartment("Computer Science");
		studentRepository.save(student);

		mockMvc.perform(get("/api/students").param("search", "ada").param("department", "Computer Science"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Ada Lovelace"))
				.andExpect(jsonPath("$[0].email").value("ada@example.edu"));
	}

	@Test
	void rejectsInvalidStudentInput() throws Exception {
		mockMvc.perform(post("/api/students")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"name":"Valid Student", "email":"valid@example.edu", "phone":"555", "rollNo":"R1", "department":"CS", "gpa":10.1}
							"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void gpaRangeIncludesTenAndRejectsValuesAboveTen() {
		StudentRequest request = new StudentRequest();
		request.setGpa(10.0);
		assertFalse(beanValidator.validate(request).stream()
				.anyMatch(error -> error.getPropertyPath().toString().equals("gpa")));

		request.setGpa(10.1);
		assertTrue(beanValidator.validate(request).stream()
				.anyMatch(error -> error.getPropertyPath().toString().equals("gpa")));
	}

	@Test
	void rejectsNullStudentRequest() throws Exception {
		mockMvc.perform(post("/api/students")
					.contentType(MediaType.APPLICATION_JSON)
					.content("null"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createsAndDeletesStudent() throws Exception {
		String student = """
				{"name":"Grace Hopper","email":"grace@example.edu","phone":"555-0111","rollNo":"2024CS102","department":"Computer Science","gpa":10.0,"attendance":98,"feeStatus":"Paid","status":"Active","admissionDate":"2024-08-15","avatarColor":"#4f46e5"}
				""";
		mockMvc.perform(post("/api/students")
					.contentType(MediaType.APPLICATION_JSON).content(student))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.gpa").value(10.0))
				.andExpect(jsonPath("$.admissionDate").value("2024-08-15"));
		Long id = studentRepository.findAll().get(0).getId();

		mockMvc.perform(delete("/api/students/{id}", id))
				.andExpect(status().isNoContent());
	}

	@Test
	void updatesExistingStudent() throws Exception {
		Student student = new Student();
		student.setName("Katherine Johnson");
		student.setEmail("katherine@example.edu");
		student.setPhone("555-0112");
		student.setRollNo("2024MA103");
		student.setDepartment("Mathematics");
		Long id = studentRepository.save(student).getId();

		mockMvc.perform(put("/api/students/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"name":"Katherine Johnson","email":"katherine@example.edu","phone":"555-0112","rollNo":"2024MA103","department":"Applied Mathematics","gpa":4.0}
							"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.department").value("Applied Mathematics"))
				.andExpect(jsonPath("$.gpa").value(4.0));
	}

	@Test
	void returnsNotFoundForMissingStudent() throws Exception {
		mockMvc.perform(get("/api/students/99999"))
				.andExpect(status().isNotFound());
	}

}
