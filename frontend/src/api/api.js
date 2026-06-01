export const API_URL = "http://localhost:8080";
export const ENDPOINTS = {
  USER: "/api/users",
  AUTH: "/api/auth",
  AUTH_LOGIN: "/api/auth/login",
  TRIP: "/api/trips",
  ROUTE: "/api/routes",
  ROUTE_POINT: "/api/route_points"
}

export const api = {
  get: (url, options = {}) =>
    apiFetch(url, { ...options, method: "GET" }),

  post: (url, body, options= {}) =>
    apiFetch(url, {
      ...options,
      method: "POST",
      body: JSON.stringify(body),
    }),

  put: (url, body, options = {}) =>
    apiFetch(url, {
      ...options,
      method: "PUT",
      body: JSON.stringify(body),
    }),

  delete: (url, options) =>
    apiFetch(url, { ...options, method: "DELETE" }),
};

function apiFetch(url, options = {}){
  const token = localStorage.getItem("token");
  return fetch(`${API_URL}${url}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers,
    }
  })
}

export function logoutUser(){
  localStorage.removeItem("token");
  window.location.href = "/login";
}