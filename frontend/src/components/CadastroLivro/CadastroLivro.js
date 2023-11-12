import React, { useState, useEffect } from 'react';
import styles from './CadastroLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CadastroLivro() {
  const [nome, setNome] = useState('');
  const [dataLancamento, setDataLancamento] = useState('');
  const [autores, setAutores] = useState([]); // Store an array of authors
  const [sumario, setSumario] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [authorOptions, setAuthorOptions] = useState([]);
  const [autorInput, setAutorInput] = useState(''); // New state to manage the author input
  const navigate = useNavigate();

  // Function to fetch author options from the backend
  const fetchAuthorOptions = async (authorName) => {
    try {
      const response = await axios.get(`http://localhost:3001/v1/author?name=${authorName}`);
      setAuthorOptions(response.data.content);
    } catch (error) {
      console.error('Error fetching author options:', error);
      setAuthorOptions([]);
    }
  };

  // useEffect to watch for changes in the "autor" input field and fetch author options
  useEffect(() => {
    if (autorInput) {
      fetchAuthorOptions(autorInput);
    } else {
      setAuthorOptions([]);
    }
  }, [autorInput]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const [dia, mes, ano] = dataLancamento.split('/');
    const dataFormatada = `${ano}-${mes}-${dia}`;
    const dataCompleta = `${dataFormatada}T00:00:00`;

    try {
      if (autores.length > 0) {
        const authorNames = autores.map((autor) => autor.name);
        console.log(nome, dataCompleta,String(authorNames),sumario)
        const response = await axios.post('http://localhost:3001/v1/book', {
          title: nome,
          releaseDate: dataCompleta,
          publisher: String(authorNames), 
          summary: sumario,
        });
        console.log(response)
        if (response.status === 201) {
          const bookId = response.data.id;

          // Iterate over the array of authors and send a request for each
          for (const autor of autores) {
            try {
              const bookAuthorResponse = await axios.post('http://localhost:3001/v1/bookAuthor', {
                bookId: bookId,
                authorId: autor.id,
                status: autor.type, // Include the author type
              });

              // Handle the response as needed
              if (bookAuthorResponse.status !== 201) {
                console.error('Erro no POST request para v1/bookAuthor:', bookAuthorResponse);
              }
            } catch (error) {
              console.error('Erro ao enviar POST request para v1/bookAuthor:', error);
            }
          }

          setShowSuccessModal(true);
        }
      } else {
        setErrorMessage('Adicione pelo menos um autor.'); // Display an error message
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

  const handleAuthorOptionClick = (selectedAuthor) => {
    setAutores([...autores, { name: selectedAuthor.name, id: selectedAuthor.id, type: 'PRIMARY' }]);
    setAuthorOptions([]); // Clear the author options list
    setAutorInput(''); // Clear the author input field
  };

  const handleAuthorTypeChange = (authorId, selectedType) => {
    setAutores((prevAutores) =>
      prevAutores.map((autor) =>
        autor.id === authorId ? { ...autor, type: selectedType } : autor
      )
    );
  };

  const handleRemoveAuthor = (authorId) => {
    setAutores((prevAutores) => prevAutores.filter((autor) => autor.id !== authorId));
  };

  const handleDataLancamentoChange = (e) => {
    const inputDate = e.target.value;
    // Check if the inputDate matches the format "dd"
    if (/^\d{2}$/.test(inputDate)) {
      setDataLancamento(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      setDataLancamento(inputDate + '/');
    } else if (/^\d{2}\/\d{2}\/\d{4}$/.test(inputDate)) {
      // If it matches "dd/MM/yyyy", update the state
      setDataLancamento(inputDate);
    } else {
      // If it doesn't match any valid format, keep the value unchanged
      setDataLancamento(inputDate);
    }
  };
  

  return (
    <div className={styles.container}>
      <h1>Cadastro de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="Data de Lançamento (dd/MM/yyyy)" value={dataLancamento} onChange={handleDataLancamentoChange} />
        <input type="text" placeholder="Pesquise o Autor" value={autorInput} onChange={(e) => setAutorInput(e.target.value)} />

        <div className={styles.authorOptions}>
          {Array.isArray(authorOptions) &&
            authorOptions.map((authorOption) => (
              <div key={authorOption.id}>
                <p onClick={() => handleAuthorOptionClick(authorOption)}>
                  {authorOption.name}
                </p>
              </div>
            ))}
        </div>

        {autores.length > 0 && (
          <div>
            <p>Autores Selecionados:</p>
            <ul>
              {autores.map((autor) => (
                <li key={autor.id}>
                  {autor.name} -{' '}
                  <select value={autor.type} onChange={(e) => handleAuthorTypeChange(autor.id, e.target.value)}>
                    <option value="PRIMARY">PRIMARY</option>
                    <option value="SECONDARY">SECONDARY</option>
                  </select>
                  <button className={styles.remover} type="button" onClick={() => handleRemoveAuthor(autor.id)}>
                    Remover
                  </button>
                </li>
              ))}
            </ul>
          </div>
        )}

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
