
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  form.addEventListener("submit", (e) => {
    const name = form.querySelector('input[type="text"]').value.trim();
    const age = form.querySelector('input[type="number"]').value.trim();
    const email = form.querySelector('input[type="email"]').value.trim();
    const password = form.querySelector('input[type="password"]').value.trim();
    const address = form.querySelector('textarea').value.trim();

    if (!name || !age || !email || !password || !address) {
      alert("Please fill in all the fields.");
      e.preventDefault();
      return;
    }

    if (parseInt(age) < 0 || isNaN(parseInt(age))) {
      alert("Please enter a valid age.");
      e.preventDefault();
      return;
    }

    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if (!emailPattern.test(email)) {
      alert("Please enter a valid email address.");
      e.preventDefault();
      return;
    }

    if (password.length < 6) {
      alert("Password must be at least 6 characters.");
      e.preventDefault();
      return;
    }

  });
});

