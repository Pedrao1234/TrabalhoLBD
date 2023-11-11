import React, { useState } from 'react';
import styles from './GerenciarUsuario.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';

function GerenciarUsuario() {
  const [cpfToSearch, setCpfToSearch] = useState('');
  const [userData, setUserData] = useState(null);
  const [showSuccessModal, setShowSuccessModal] = useState(false); // Modal de sucesso
  const [showErrorModal, setShowErrorModal] = useState(false); // Modal de erro
  const [errorMessage, setErrorMessage] = useState(''); // Mensagem de erro
  const [successMessage, setSuccessMessage] = useState(''); // Mensagem de erro
  const [date, setDate]= useState('');
  const navigate = useNavigate();

  const handleSearch = async () => {
    try {
      const response = await axios.get(`http://localhost:3001/v1/reader?cpf=${cpfToSearch}`);

      if(response.data.content[0] === undefined){
        setErrorMessage('O usuário não existe!');
        setShowErrorModal(true);
        return;
      }
      setUserData(response.data.content[0]);
      setDate(format(new Date(response.data.content[0].dateBirth), 'dd/MM/yyyy'))
    } catch (error) {
        setErrorMessage('O usuário não existe!');
        setShowErrorModal(true);
        console.error('Erro ao buscar usuário:', error);
      // Handle error (show a message to the user, etc.)
    }
  };

  const handleSaveChanges = async () => {
    try {

        const dateRegex = /^\d{2}\/\d{2}\/\d{4}$/;
        if (!dateRegex.test(date)) {
          setErrorMessage('Formato de data de nascimento inválido. Use o formato "dd/MM/yyyy".');
          setShowErrorModal(true);
          return;
        }

        const [dia, mes, ano] = date.split("/");
  
        const dataFormatada = `${ano}-${mes}-${dia}`;
    
      
        const dataCompleta = `${dataFormatada}T00:00:00`;
      
        const response = await axios.patch(`http://localhost:3001/v1/reader/${userData.id}`, 
      {
        "name": userData.name,
        "dateBirth": dataCompleta,
        "cpf": userData.cpf
      });
      if(response.status === 200){
        setSuccessMessage("Dados alterados com sucesso!")
        setShowSuccessModal(true); // Mostra o modal de sucesso
      }
    } catch (error) {
      console.error('Erro ao salvar alterações:', error);
      // Handle error (show a message to the user, etc.)
    }
  };

  const handleDeleteUser = async () => {
    try {
        const response = await axios.delete(`http://localhost:3001/v1/reader/${userData.id}`);
        if(response.status === 204){
            setSuccessMessage("Usuário deletado com sucesso!")
            setShowSuccessModal(true); // Mostra o modal de sucesso
        }
         
    } catch (error) {
      console.error('Erro ao deletar usuário:', error);
      // Handle error (show a message to the user, etc.)
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false); // Fecha o modal de sucesso
    navigate('/'); // Redireciona para a página inicial, se desejado
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false); // Fecha o modal de erro
  };

  const handleInputChange = (field, value) => {
    setUserData((prevData) => ({ ...prevData, [field]: value }));
  };

   // Função para formatar a data enquanto o usuário digita
   const handleDataNascimentoChange = (e) => {
    const inputDate = e.target.value;
    if (/^\d{2}$/.test(inputDate)) {
      // Adiciona uma barra após os dois primeiros dígitos
      setDate(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      // Adiciona uma barra após os quatro primeiros dígitos
      setDate(inputDate + '/');
    } else {
      // Mantém o valor inalterado se não corresponder a um formato válido
      setDate(inputDate);
    }
  };

  return (
    <div className={styles.container}>
      <h1>Gerenciar Usuário</h1>
      <div>
        <label>Buscar CPF:</label>
        <input type="text" value={cpfToSearch} onChange={(e) => setCpfToSearch(e.target.value)} />
        <button onClick={handleSearch}>Buscar</button>
      </div>

      {userData && (
        <div>
          <label>CPF:</label>
          <input type="text" value={userData.cpf} onChange={(e) => handleInputChange('cpf', e.target.value)}/>

          <label>Nome:</label>
          <input type="text" value={userData.name} onChange={(e) => handleInputChange('name', e.target.value)} />

          <label>Data de Nascimento:</label>
          <input type="text" value={date} onChange={handleDataNascimentoChange} />

          <button className={styles.botao} onClick={handleSaveChanges}>Salvar Alterações</button>
          <button onClick={handleDeleteUser}>Deletar Usuário</button>
        </div>
      )}
       {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>{successMessage}</p>
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

export default GerenciarUsuario;
