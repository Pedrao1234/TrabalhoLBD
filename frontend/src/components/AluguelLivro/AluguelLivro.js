import React, { useState, useEffect } from 'react';
import styles from './AluguelLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AluguelLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataAluguel, setDataAluguel] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const [livrosSugeridos, setLivrosSugeridos] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const [diaAluguel, mesAluguel, anoAluguel] =dataAluguel.split("/");
 
    const dataFormatadaAluguel = `${anoAluguel}-${mesAluguel}-${diaAluguel}`;

    const dataCompletaAluguel = `${dataFormatadaAluguel}T00:00:00`;

    const [diaDevolucao, mesDevolucao, anoDevolucao] = dataDevolucao.split("/");
 
    const dataFormatadaDevolucao = `${anoDevolucao}-${mesDevolucao}-${diaDevolucao}`;

    const dataCompletaDevolucao = `${dataFormatadaDevolucao}T00:00:00`;
    try {
      const response = await axios.post('http://localhost:3001/v1/rent', {
        "readerId":usuario,
        "bookId":livro,
        "rentDate": dataCompletaAluguel,
        "devolutionDate":dataCompletaDevolucao,
      });

      if (response.status === 201) {
        setShowSuccessModal(true);
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao efetuar o aluguel. Por favor, tente novamente.');
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

  // Função para formatar a data enquanto o usuário digita
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

  // Função para formatar a data de devolução enquanto o usuário digita
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

  useEffect(() => {
    // Busque a lista de livros do seu sistema a partir de uma API ou outra fonte de dados em tempo real
    async function fetchLivros() {
      try {
        const response = await axios.get('http://localhost:3001/v1/rent');
        const livrosDoSistema = response.data;
        setLivrosSugeridos(livrosDoSistema);
      } catch (error) {
        console.error('Erro ao buscar a lista de livros:', error);
      }
    }

    fetchLivros();
  }, []);

  const handleLivrosChange = (e) => {
    const inputLivro = e.target.value;
    setLivro(inputLivro);
  }

  return (
    <div className={styles.container}>
      <h1>Aluguel de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome do Usuário" value={usuario} onChange={(e) => setUsuario(e.target.value)} />
        <input type="text" placeholder="Nome do Livro" value={livro} onChange={handleLivrosChange} />
        <ul>
        {livrosSugeridos.map((sugestao, index) => (
          <li key={index} onClick={() => setLivro(sugestao)}>
            {sugestao}
          </li>
        ))}
      </ul>
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
