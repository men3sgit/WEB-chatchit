export default function authHeader() {
  const data = JSON.parse(localStorage.getItem("authUser"));

  if (data && data.accessToken) {
    return { Authorization: data.accessToken };
  } else {
    return {};
  }
}
