import React, { useState, useEffect } from 'react';
import styles from './CadastroLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CadastroLivro() {
  const [nome, setNome] = useState('');
  const [dataLancamento, setDataLancamento] = useState('');
  const [autor, setAutor] = useState({ name: '', id: '' }); // Store both name and ID
  const [sumario, setSumario] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [authorOptions, setAuthorOptions] = useState([]); // Initialize as an empty array
  const navigate = useNavigate();

  // Function to fetch author options from the backend
  const fetchAuthorOptions = async (authorName) => {
    try {
      const response = await axios.get(`http://localhost:3001/v1/author?name=${authorName}`);
      setAuthorOptions(response.data.content); // Update the state with the fetched author options
    } catch (error) {
      console.error('Error fetching author options:', error);
      setAuthorOptions([]); // Clear the author options in case of an error
    }
  };

  // useEffect to watch for changes in the "autor" input field and fetch author options
  useEffect(() => {
    if (autor.name) {
      fetchAuthorOptions(autor.name);
    } else {
      setAuthorOptions([]); // Clear the author options when there's no input
    }
  }, [autor.name]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const [dia, mes, ano] = dataLancamento.split('/');
    const dataFormatada = `${ano}-${mes}-${dia}`;
    const dataCompleta = `${dataFormatada}T00:00:00`;

    try {
      // Check if both name and id are available in the selected author
      if (autor.name && autor.id) {
        const response = await axios.post('http://localhost:3001/v1/book', {
          title: nome,
          releaseDate: dataCompleta,
          publisher: autor.name, // Send the author's name
          summary: sumario,
        });

        if (response.status === 201) {
          const bookId = response.data.id;
          try {
            const bookAuthorResponse = await axios.post('http://localhost:3001/v1/bookAuthor', {
              bookId: bookId, 
              authorId: autor.id, 
            });
        
            // Handle the response as needed
            if (bookAuthorResponse.status === 201) {
              setShowSuccessModal(true);
            } else {
              console.error('Erro no POST request para v1/bookAuthor:', bookAuthorResponse);
            }
          } catch (error) {
            console.error('Erro ao enviar POST request para v1/bookAuthor:', error);
          }
          
        }
      } else {
        setErrorMessage('Selecione um autor válido.'); // Display an error message
        setShowErrorModal(true);
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao efetuar o cadastro de livro. Por favor, tente novamente.');
      setShowErrorModal(true);
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false);
    navigate('/');
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false);
  };

  const handleDataLancamentoChange = (e) => {
    const inputDate = e.target.value;
    if (/^\d{2}$/.test(inputDate)) {
      setDataLancamento(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      setDataLancamento(inputDate + '/');
    } else {
      setDataLancamento(inputDate);
    }
  };

  const handleAutorChange = (e) => {
    const inputAutor = e.target.value;
    setAutor({ name: inputAutor, id: '' }); // Clear the author ID when the input changes
  };

  const handleAuthorOptionClick = (selectedAuthor) => {
    // Update the selected author with both name and ID
    setAutor({ name: selectedAuthor.name, id: selectedAuthor.id });
    setAuthorOptions([]); // Clear the author options list
  };

  return (
    <div className={styles.container}>
      <h1>Cadastro de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="Data de Lançamento (dd/MM/yyyy)" value={dataLancamento} onChange={handleDataLancamentoChange} />
        <input type="text" placeholder="Autor" value={autor.name} onChange={handleAutorChange} />

        <div className={styles.authorOptions}>
          {Array.isArray(authorOptions) &&
            authorOptions.map((authorOption) => (
              <div key={authorOption.id} onClick={() => handleAuthorOptionClick(authorOption)}>
                {authorOption.name}
              </div>
            ))}
        </div>

        <textarea placeholder="Sumário" value={sumario} onChange={(e) => setSumario(e.target.value)} />
        <button type="submit">Cadastrar</button>
      </form>

      {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Cadastro de livro efetuado com sucesso!</p>
            <button onClick={handleSuccessModalOkClick}>Ok</button>
          </div>
        </div>
      )}

      {showErrorModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>{errorMessage}</p>
            <button onClick={handleErrorModalOkClick}>Ok</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default CadastroLivro;
