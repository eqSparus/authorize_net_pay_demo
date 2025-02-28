const TEST_PAYMNEY_URL = "https://test.authorize.net/payment/payment?token=";

document.querySelector("#payment1").addEventListener("click", () => {
  processPayment(100);
});

document.querySelector("#payment2").addEventListener("click", () => {
  processPayment(200);
});

document.querySelector("#payment3").addEventListener("click", () => {
  processPayment(300);
});

function processPayment(amount) {
  fetch("http://localhost:8080/api/payment/token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    body: JSON.stringify({ amount: amount }),
  })
    .then((res) => {
      const reponse = res.json();
      console.log("Платеж прошел успешно", reponse);
      alert("Платеж прошел успешно");
    })
    .catch((error) => {
      console.log(error);
      alert(error.message);
    });
}

function navigatPaymentPage(token) {
  document.location.href = TEST_PAYMNEY_URL + token;
}
