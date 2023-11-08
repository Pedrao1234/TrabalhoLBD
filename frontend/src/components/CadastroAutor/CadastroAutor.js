import React, { useState } from 'react';
import styles from './CadastroAutor.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


function CadastroAutor() {
  const [nome, setNome] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [sexo, setSexo] = useState('MALE');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {

    e.preventDefault();
    const [dia, mes, ano] = dataNascimento.split("/");
 
    const dataFormatada = `${ano}-${mes}-${dia}`;

   
    const dataCompleta = `${dataFormatada}T00:00:00`;
    console.log(nome, dataCompleta, sexo)
    try {
      const response = await axios.post('http://localhost:3001/v1/author', {
        "name": nome,
        "birthDate": dataCompleta,
        "sex": sexo
      });

      if (response.status === 201) {
        setShowSuccessModal(true);
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao efetuar o cadastro de autor. Por favor, tente novamente.');
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
  const handleDataNascimentoChange = (e) => {
    const inputDate = e.target.value;
    if (/^\d{2}$/.test(inputDate)) {
      setDataNascimento(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      setDataNascimento(inputDate + '/');
    } else {
      setDataNascimento(inputDate);
    }
  };

  return (
    <div className={styles.container}>
      <h1>Cadastro de Autor</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="Data de Nascimento (dd/MM/yyyy)" value={dataNascimento} onChange={handleDataNascimentoChange} />
        <select value={sexo} onChange={(e) => setSexo(e.target.value)}>
          <option value="MALE">MALE</option>
          <option value="FEMALE">FEMALE</option>
        </select>

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
