import React, { useState, useEffect } from 'react';
import styles from './AluguelLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AluguelLivro() {
  const [cpf, setCPF] = useState('');
  const [bookName, setBookName] = useState('');
  const [bookId, setBookId] = useState('');
  const [dataAluguel, setDataAluguel] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const [livrosSugeridos, setLivrosSugeridos] = useState([]);
  const [submitEnabled, setSubmitEnabled] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Check if an author has been selected
    if (!bookId || submitEnabled===false) {
      setShowErrorModal(true);
      setErrorMessage('Selecione um livro antes de enviar o formulário.');
      return;
    }

    const [diaAluguel, mesAluguel, anoAluguel] = dataAluguel.split("/");
    const dataFormatadaAluguel = `${anoAluguel}-${mesAluguel}-${diaAluguel}`;
    const dataCompletaAluguel = `${dataFormatadaAluguel}T00:00:00`;

    const [diaDevolucao, mesDevolucao, anoDevolucao] = dataDevolucao.split("/");
    const dataFormatadaDevolucao = `${anoDevolucao}-${mesDevolucao}-${diaDevolucao}`;
    const dataCompletaDevolucao = `${dataFormatadaDevolucao}T00:00:00`;

    try {
      const readerResponse = await axios.get(`http://localhost:3001/v1/reader?cpf=${cpf}`);

    try {
      const response = await axios.get(`http://localhost:3001/v1/rent?readerId=${readerResponse.data.content[0].id}`);
      if (response.data.content=== "") {
      setErrorMessage('Esse usuário já tem um alugel em andamento.');
      setShowErrorModal(true); 
      return
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao efetuar o aluguel. Por favor, tente novamente.');
      setShowErrorModal(true); 
    }

      // PROCURAR O LIVRO NA TABELA RENT
       try {
        const bookResponse = await axios.get(`http://localhost:3001/v1/book/${bookId}`);
        if (bookResponse.data.status !== "AVAILABLE"){
          setErrorMessage('Esse livro ja está alugado. Por favor, tente novamente.');
          setShowErrorModal(true);
          return;
        }
      } catch (error) {
        console.error('Erro ao enviar POST request:', error);
        setErrorMessage('Erro ao efetuar o aluguel. Por favor, tente novamente.');
        setShowErrorModal(true);
      }


      try {
        console.log(readerResponse.data.content[0].id,[bookId], dataCompletaAluguel, dataCompletaDevolucao)
        const response = await axios.post('http://localhost:3001/v1/rent', {
          "readerId": readerResponse.data.content[0].id,
          "bookId": [bookId],
          "rentDate": dataCompletaAluguel,
          "devolutionDate": dataCompletaDevolucao,
        });
  
        if (response.status === 201) {
          setShowSuccessModal(true);
        }
      } catch (error) {
        console.error('Erro ao enviar POST request:', error);
        setErrorMessage('Esse usuário já tem um alugel em andamento.');
        setShowErrorModal(true);
      }

    
    } catch (error) {
      console.error('Error fetching readers options:', error);
      setErrorMessage('Esse usuário não existe. Por favor, tente novamente.');
      setShowErrorModal(true);
    }



  };

  const handleDataAluguelChange = (e) => {
    const inputDate = e.target.value;
    if (/^\d{2}$/.test(inputDate)) {
      setDataAluguel(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      setDataAluguel(inputDate + '/');
    } else {
      setDataAluguel(inputDate);
    }
  };

  const handleDataDevolucaoChange = (e) => {
    const inputDate = e.target.value;
    if (/^\d{2}$/.test(inputDate)) {
      setDataDevolucao(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      setDataDevolucao(inputDate + '/');
    } else {
      setDataDevolucao(inputDate);
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false);
    navigate('/');
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false);
  };

  const handleBookNameChange = (e) => {
    const inputBookName = e.target.value;
    setBookName(inputBookName);
    setSubmitEnabled(false); // Disable the submit button when typing
  };

  const handleBookSuggestionClick = (livroSugerido) => {
    setBookName(livroSugerido.title);
    setBookId(livroSugerido.id);
    setLivrosSugeridos([]); // Clear book suggestions
    setSubmitEnabled(true); // Enable the submit button when an author is selected
  };

  useEffect(() => {
    if (bookName) {
      fetchBookSuggestions(bookName);
    } else {
      setLivrosSugeridos([]);
    }
  }, [bookName]);

  const fetchBookSuggestions = async (bookName) => {
    try {
      const response = await axios.get(`http://localhost:3001/v1/book?title=${bookName}`);
      setLivrosSugeridos(response.data.content);
    } catch (error) {
      console.error('Error fetching book suggestions:', error);
      setLivrosSugeridos([]);
    }
  };

  return (
    <div className={styles.container}>
      <h1>Aluguel de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="CPF" value={cpf} onChange={(e) => setCPF(e.target.value)} />
        <input type="text" placeholder="Nome do Livro" value={bookName} onChange={handleBookNameChange} />
        <div className={styles.bookSuggestions}>
          {Array.isArray(livrosSugeridos) &&
            livrosSugeridos.map((livroSugerido) => (
              <div key={livroSugerido.id} onClick={() => handleBookSuggestionClick(livroSugerido)}>
                {livroSugerido.title} - {livroSugerido.publisher}
              </div>
            ))}
        </div>

        <input type="text" placeholder="Data de Aluguel (dd/MM/yyyy)" value={dataAluguel} onChange={handleDataAluguelChange} />
        <input type="text" placeholder="Data de Devolução (dd/MM/yyyy)" value={dataDevolucao} onChange={handleDataDevolucaoChange} />
        <button type="submit">Alugar</button>
      </form>

      {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Aluguel efetuado com sucesso!</p>
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

export default AluguelLivro;
