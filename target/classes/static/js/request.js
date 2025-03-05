
function sendRecaptchaResponse() {
    const recaptchaResponse = grecaptcha.getResponse();
    if (!recaptchaResponse) {
        alert("Veuillez compléter le reCAPTCHA.");
        return;
    }
    const data = {
        recaptchaResponse: recaptchaResponse
    };
    fetch('http://localhost:8080/api/validateRecaptcha', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("reCAPTCHA validé avec succès !");
            } else {
                alert("La validation reCAPTCHA a échoué.");
            }
        })
        .catch(error => {
            console.error("Erreur lors de l'envoi de la requête reCAPTCHA:", error);
            alert("Erreur de communication avec le serveur.");
        });
}
document.getElementById('votreFormulaire').addEventListener('submit', function(event) {
    event.preventDefault();
    sendRecaptchaResponse();
});
