function uploadCSV() {
    var uploadButton = document.getElementById('uploadButton');
    var loader = document.getElementById('loader');
    var formData = new FormData(document.getElementById('uploadForm'));
    
    uploadButton.disabled = true;
    loader.style.display = 'inline-block'; // Mostra o spinner ao lado do botão
    
    fetch('http://localhost:8080/assistant/update/catalog', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Ocorreu algum erro com o servidor!');
        }
        return response.text();
    })
    .then(data => {
        console.log('Sucesso:', data);
        alert(data);
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Atualização falhou: ' + error.message);
    })
    .finally(() => {
        uploadButton.disabled = false;
        loader.style.display = 'none'; // Esconde o spinner
    });
}
