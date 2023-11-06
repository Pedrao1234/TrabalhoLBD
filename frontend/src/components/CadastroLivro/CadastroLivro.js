import React, { useState } from 'react';
import styles from './CadastroLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


function CadastroLivro() {
  const [nome, setNome] = useState('');
  const [dataLancamento, setDataLancamento] = useState('');
  const [autor, setAutor] = useState('');
  const [sumario, setSumario] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/seu-endpoint-de-post', {
        nome,
        dataLancamento,
        autor,
        sumario,
      });

      if (response.status === 200) {
        setShowSuccessModal(true);
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

  // Função para formatar a data enquanto o usuário digita
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

  return (
    <div className={styles.container}>
      <h1>Cadastro de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="Data de Lançamento (dd/MM/yyyy)" value={dataLancamento} onChange={handleDataLancamentoChange} />
        <input type="text" placeholder="Autor" value={autor} onChange={(e) => setAutor(e.target.value)} />
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
