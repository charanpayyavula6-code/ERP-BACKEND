const API_BASE_URL = "http://localhost:8080/api/students";
const AUTH_BASE_URL = "http://localhost:8080/api/auth";
let accessToken = null;
let refreshToken = null;

async function request(url, options = {}, allowRefresh = true) {
  const response = await fetch(url, {
    ...options,
    headers: {
      Accept: "application/json",
      ...(options.body ? { "Content-Type": "application/json" } : {}),
      ...(accessToken ? { Authorization: `Bearer ${accessToken}` } : {}),
      ...options.headers,
    },
  });

  if (response.status === 401 && allowRefresh && refreshToken && !url.startsWith(AUTH_BASE_URL)) {
    await refreshAccessToken();
    return request(url, options, false);
  }

  if (response.status === 204) {
    return null;
  }

  const body = await response.json().catch(() => null);

  if (!response.ok) {
    const validationMessage = body?.details
      ? Object.entries(body.details)
          .map(([field, message]) => `${field}: ${message}`)
          .join("; ")
      : null;

    throw new Error(
      validationMessage ||
        body?.message ||
        body?.error ||
        `Request failed with status ${response.status}`,
    );
  }

  return body;
}

export async function login(username, password) {
  const tokens = await request(`${AUTH_BASE_URL}/token`, {
    method: "POST",
    body: JSON.stringify({ username, password }),
  }, false);
  accessToken = tokens.accessToken;
  refreshToken = tokens.refreshToken;
  return tokens;
}

export async function refreshAccessToken() {
  if (!refreshToken) {
    throw new Error("You are not logged in");
  }

  try {
    const tokens = await request(`${AUTH_BASE_URL}/refresh`, {
      method: "POST",
      body: JSON.stringify({ refreshToken }),
    }, false);
    accessToken = tokens.accessToken;
    refreshToken = tokens.refreshToken;
    return tokens;
  } catch (error) {
    accessToken = null;
    refreshToken = null;
    throw error;
  }
}

export async function logout() {
  const currentRefreshToken = refreshToken;
  accessToken = null;
  refreshToken = null;

  if (!currentRefreshToken) return;

  await request(`${AUTH_BASE_URL}/logout`, {
    method: "POST",
    body: JSON.stringify({ refreshToken: currentRefreshToken }),
  }, false);
}

export function getStudents({ search, department } = {}) {
  const params = new URLSearchParams();

  if (search) params.set("search", search);
  if (department) params.set("department", department);

  const query = params.toString();
  const url = query ? `${API_BASE_URL}?${query}` : API_BASE_URL;

  return request(url);
}

export function getStudentById(id) {
  return request(`${API_BASE_URL}/${encodeURIComponent(id)}`);
}

export function createStudent(student) {
  return request(API_BASE_URL, {
    method: "POST",
    body: JSON.stringify(student),
  });
}

export function updateStudent(id, student) {
  return request(`${API_BASE_URL}/${encodeURIComponent(id)}`, {
    method: "PUT",
    body: JSON.stringify(student),
  });
}

export function deleteStudent(id) {
  return request(`${API_BASE_URL}/${encodeURIComponent(id)}`, {
    method: "DELETE",
  });
}