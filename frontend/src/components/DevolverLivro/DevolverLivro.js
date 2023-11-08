import React, { useState } from 'react';
import styles from './DevolverLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';

function DevolverLivro() {
  const [cpf, setCPF] = useState('');
  const [rentInfo, setRentInfo] = useState(null);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleCPFFormSubmit = async (e) => {
    e.preventDefault();

    try {
      const userResponse = await axios.get(`http://localhost:3001/v1/reader?cpf=${cpf}`);
     
      const userId = userResponse.data.content[0].id;
   

      const rentResponse = await axios.get(`http://localhost:3001/v1/rent?readerId=${userId}`);
      const rentData = rentResponse.data.content[0]; // Assuming the first element in the content array

      if (rentData === undefined) {
        setErrorMessage("Esse usuário não posssui livros alugados.")
        setShowErrorModal(true)
        return
      }
      // Exibir informações do registro de aluguel e o botão "Confirmar Devolução"
      rentData.rentDate = format(new Date(rentData.rentDate), 'dd-MM-yyyy');
      rentData.devolutionDate = format(new Date(rentData.devolutionDate), 'dd-MM-yyyy');
      setRentInfo(rentData);

    } catch (error) {
        setErrorMessage("Esse usuário não existe.")
        setShowErrorModal(true)
      console.error('Erro ao buscar informações de aluguel:', error);
    }
  };

  const handleConfirmDevolucao = async () => {
    try {
      await axios.patch(`http://localhost:3001/v1/rent/${rentInfo.id}`, {
        "status": 'FINISHED'
      });

      await axios.delete(`http://localhost:3001/v1/rent/${rentInfo.id}`);

      setShowSuccessModal(true);
    } catch (error) {
      console.error('Erro ao confirmar a devolução:', error);
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false);
    navigate('/');
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false);
  };

  return (
    <div className={styles.container}>
      <h1>Devolução de Livro</h1>
      <form onSubmit={handleCPFFormSubmit}>
        <input type="text" placeholder="CPF do Usuário" value={cpf} onChange={(e) => setCPF(e.target.value)} />
        <button type="submit">Pesquisar</button>
      </form>

      {rentInfo && (
        <div>
          <h2>Informações do Aluguel</h2>
          <p>Nome do Livro: {rentInfo.book[0].title}</p>
          <p>Nome do Autor: {rentInfo.book[0].publisher}</p>
          <p>Nome do locador: {rentInfo.reader.name}</p>
          <p>Data de locação: {rentInfo.rentDate}</p>
          <p>Data de Devolução: {rentInfo.devolutionDate}</p>
   
          <button onClick={handleConfirmDevolucao}>Confirmar Devolução</button>
        </div>
      )}

      {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Devolução feita com sucesso</p>
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
