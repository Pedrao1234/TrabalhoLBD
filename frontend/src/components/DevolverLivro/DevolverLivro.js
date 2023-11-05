import React, { useState } from 'react';
import styles from './DevolverLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function DevolverLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false); // Modal de sucesso
  const [showErrorModal, setShowErrorModal] = useState(false); // Modal de erro
  const [errorMessage, setErrorMessage] = useState(''); // Mensagem de erro
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/seu-endpoint-de-post', {
        usuario,
        livro,
        dataDevolucao,
      });

      if (response.status === 200) {
        setShowSuccessModal(true); // Mostra o modal de sucesso
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao registrar a devolução do livro. Por favor, tente novamente.');
      setShowErrorModal(true); // Mostra o modal de erro
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false); // Fecha o modal de sucesso
    navigate('/');
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false); // Fecha o modal de erro
  };

  return (
    <div className={styles.container}>
      <h1>Devolução de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome do Usuário" value={usuario} onChange={(e) => setUsuario(e.target.value)} />
        <input type="text" placeholder="Nome do Livro" value={livro} onChange={(e) => setLivro(e.target.value)} />
        <input type="text" placeholder="Data de Devolução" value={dataDevolucao} onChange={(e) => setDataDevolucao(e.target.value)} />
        <button type="submit">Registrar Devolução</button>
      </form>

      {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Devolução registrada com sucesso</p>
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

export default DevolverLivro;
