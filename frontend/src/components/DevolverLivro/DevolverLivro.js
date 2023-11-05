import React, { useState } from 'react';
import styles from './DevolverLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function DevolverLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');
  const [showModal, setShowModal] = useState(false); // Estado para controlar a exibição do modal
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Faça a lógica para registrar a devolução do livro no sistema e enviar os dados para o backend aqui
      const response = await axios.post('/seu-endpoint-de-post', {
        usuario,
        livro,
        dataDevolucao,
      });

      if (response.status === 200) {
        setShowModal(true); // Mostra o modal de sucesso
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
    }
  };

  const handleModalOkClick = () => {
    setShowModal(false); // Fecha o modal de sucesso
    navigate('/'); 
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

      {showModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Devolução registrada com sucesso</p>
            <button onClick={handleModalOkClick}>Ok</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default DevolverLivro;
