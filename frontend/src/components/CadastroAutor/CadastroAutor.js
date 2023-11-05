import React, { useState } from 'react';
import styles from './CadastroAutor.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CadastroAutor() {
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [sexo, setSexo] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false); // Modal de sucesso
  const [showErrorModal, setShowErrorModal] = useState(false); // Modal de erro
  const [errorMessage, setErrorMessage] = useState(''); // Mensagem de erro
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/seu-endpoint-de-post', {
        nome,
        cpf,
        dataNascimento,
        sexo,
      });

      if (response.status === 200) {
        setShowSuccessModal(true); // Mostra o modal de sucesso
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao efetuar o cadastro de autor. Por favor, tente novamente.');
      setShowErrorModal(true); // Mostra o modal de erro
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false); // Fecha o modal de sucesso
    navigate('/'); // Redireciona para a página inicial
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false); // Fecha o modal de erro
  };

  return (
    <div className={styles.container}>
      <h1>Cadastro de Autor</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="CPF" value={cpf} onChange={(e) => setCpf(e.target.value)} />
        <input type="text" placeholder="Data de Nascimento" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} />
        <input type="text" placeholder="Sexo" value={sexo} onChange={(e) => setSexo(e.target.value)} />
        <button type="submit">Cadastrar</button>
      </form>

      {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Cadastro de autor efetuado com sucesso!</p>
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

export default CadastroAutor;
