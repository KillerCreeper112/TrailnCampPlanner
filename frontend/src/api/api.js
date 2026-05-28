const API_URL = "http://localhost:8080";

export async function apiFetch(url, options = {}){
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