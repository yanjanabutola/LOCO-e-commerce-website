document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  form.addEventListener("submit", (e) => {
    const email = form.querySelector('input[type="email"]').value.trim();
    const password = form.querySelector('input[type="password"]').value.trim();

    if (!email || !password) {
      alert("Please fill in both email and password.");
      e.preventDefault();
      return;
    }

    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if (!emailPattern.test(email)) {
      alert("Please enter a valid email address.");
      e.preventDefault();
      return;
    }
  });
});

