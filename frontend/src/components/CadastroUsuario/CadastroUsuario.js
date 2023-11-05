import React, { useState } from 'react';
import styles from './CadastroUsuario.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CadastroUsuario() {
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [showModal, setShowModal] = useState(false); // Estado para controlar a exibição do modal
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Faça o POST request aqui
      const response = await axios.post('/seu-endpoint-de-post', {
        nome,
        cpf,
        dataNascimento,
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
    navigate('/'); // Redireciona para a página inicial, se desejado
  };

  return (
    <div className={styles.container}>
      <h1>Cadastro de Usuário</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="CPF" value={cpf} onChange={(e) => setCpf(e.target.value)} />
        <input type="text" placeholder="Data de Nascimento" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} />
        <button type="submit">Cadastrar</button>
      </form>

      {showModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Cadastro de leitor efetuado com sucesso!</p>
            <button onClick={handleModalOkClick}>Ok</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default CadastroUsuario;
