import React, { useState } from 'react';
import styles from './AluguelLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AluguelLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataAluguel, setDataAluguel] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');
  const [showModal, setShowModal] = useState(false); // Estado para controlar a exibição do modal
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/seu-endpoint-de-post', {
        usuario,
        livro,
        dataAluguel,
        dataDevolucao,
      });

      if (response.status === 200) {
        setShowModal(true); // Mostra o modal
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
    }
  };

  const handleModalOkClick = () => {
    setShowModal(false); // Fecha o modal
    navigate('/'); // Redireciona para a página inicial
  };

  return (
    <div className={styles.container}>
      <h1>Aluguel de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome do Usuário" value={usuario} onChange={(e) => setUsuario(e.target.value)} />
        <input type="text" placeholder="Nome do Livro" value={livro} onChange={(e) => setLivro(e.target.value)} />
        <input type="text" placeholder="Data de Aluguel" value={dataAluguel} onChange={(e) => setDataAluguel(e.target.value)} />
        <input type="text" placeholder="Data de Devolução" value={dataDevolucao} onChange={(e) => setDataDevolucao(e.target.value)} />
        <button type="submit">Alugar</button>
      </form>

      {showModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Aluguel efetuado com sucesso!</p>
            <button onClick={handleModalOkClick}>Ok</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default AluguelLivro;
