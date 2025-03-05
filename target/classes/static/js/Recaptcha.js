document.addEventListener("DOMContentLoaded", function() {
    // Sélectionne le bon formulaire
    var formulaire = document.getElementById("recaptcha-form");

    if (formulaire) {
        formulaire.addEventListener("submit", envoyerFormulaire);
    }
});

function envoyerFormulaire(event) {
    event.preventDefault();  // Empêche l'envoi traditionnel du formulaire

    var responseRecaptcha = grecaptcha.getResponse();
    if (!responseRecaptcha) {
        alert("Veuillez valider le reCAPTCHA.");
        return;
    }

    var formData = new FormData(document.getElementById("recaptcha-form"));

    // Ajoute la réponse reCAPTCHA
    formData.append("recaptcha_response", responseRecaptcha);

    fetch("/verifier-recaptcha", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Formulaire validé !");
                // Redirection ou autre action après succès
                window.location.href = "/page-de-confirmation.html";
            } else {
                alert("Vérification reCAPTCHA échouée. Veuillez réessayer.");
            }
        })
        .catch(error => {
            console.error("Erreur lors de la vérification reCAPTCHA :", error);
            alert("Une erreur est survenue, veuillez réessayer plus tard.");
        });
}
