import React, { useState } from 'react';
import styles from './CadastroUsuario.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';

function CadastroUsuario() {
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false); // Modal de sucesso
  const [showErrorModal, setShowErrorModal] = useState(false); // Modal de erro
  const [errorMessage, setErrorMessage] = useState(''); // Mensagem de erro
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (cpf.length !== 11) {
      setErrorMessage('O CPF deve conter exatamente 11 caracteres.');
      setShowErrorModal(true); // Mostra o modal de erro
      return;
    }
      // Verifica se a dataNascimento é uma string em um formato válido, por exemplo, "dd/MM/yyyy"
    const dateRegex = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!dateRegex.test(dataNascimento)) {
      setErrorMessage('Formato de data de nascimento inválido. Use o formato "dd/MM/yyyy".');
      setShowErrorModal(true);
      return;
    }

    let formattedDataNascimento = ""
    // Formata a data de nascimento
    try {   
      formattedDataNascimento = format(new Date(dataNascimento), 'dd/MM/yyyy');
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Formato de data de nascimento inválido. Use o formato "dd/MM/yyyy".');
      setShowErrorModal(true); // Mostra o modal de erro
      return;
    }


    const [dia, mes, ano] = formattedDataNascimento.split("/");
  
    const dataFormatada = `${ano}-${mes}-${dia}`;

  
    const dataCompleta = `${dataFormatada}T00:00:00`;
    console.log(nome,dataCompleta,cpf)
    try {
      const response = await axios.post('http://localhost:3001/v1/reader', {
        "name": nome,
        "dateBirth": dataCompleta,
        "cpf":cpf
      });

      if (response.status === 201) {
        setShowSuccessModal(true); // Mostra o modal de sucesso
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao efetuar o cadastro de usuário. Por favor, tente novamente.');
      setShowErrorModal(true); // Mostra o modal de erro
    }
  };

  const handleSuccessModalOkClick = () => {
    setShowSuccessModal(false); // Fecha o modal de sucesso
    navigate('/'); // Redireciona para a página inicial, se desejado
  };

  const handleErrorModalOkClick = () => {
    setShowErrorModal(false); // Fecha o modal de erro
  };

    // Função para formatar a data enquanto o usuário digita
    const handleDataNascimentoChange = (e) => {
      const inputDate = e.target.value;
      if (/^\d{2}$/.test(inputDate)) {
        // Adiciona uma barra após os dois primeiros dígitos
        setDataNascimento(inputDate + '/');
      } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
        // Adiciona uma barra após os quatro primeiros dígitos
        setDataNascimento(inputDate + '/');
      } else {
        // Mantém o valor inalterado se não corresponder a um formato válido
        setDataNascimento(inputDate);
      }
    };

  return (
    <div className={styles.container}>
      <h1>Cadastro de Usuário</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <input type="text" placeholder="CPF" value={cpf} onChange={(e) => setCpf(e.target.value)} />
        <input type="text" placeholder="Data de Nascimento (dd/MM/yyyy)" value={dataNascimento} onChange={handleDataNascimentoChange} />
        <button type="submit">Cadastrar</button>
      </form>

      {showSuccessModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <p>Cadastro de usuário efetuado com sucesso!</p>
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

export default CadastroUsuario;