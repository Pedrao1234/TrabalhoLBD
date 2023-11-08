import React, { useState, useEffect } from 'react';
import styles from './DevolverLivro.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';

function DevolverLivro() {
  const [usuario, setUsuario] = useState('');
  const [livro, setLivro] = useState('');
  const [dataDevolucao, setDataDevolucao] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  // Função para formatar a data enquanto o usuário digita
  const handleDataDevolucaoChange = (e) => {
    const inputDate = e.target.value;
    if (/^\d{2}$/.test(inputDate)) {
      // Adiciona uma barra após os dois primeiros dígitos
      setDataDevolucao(inputDate + '/');
    } else if (/^\d{2}\/\d{2}$/.test(inputDate)) {
      // Adiciona uma barra após os quatro primeiros dígitos
      setDataDevolucao(inputDate + '/');
    } else {
      // Mantém o valor inalterado se não corresponder a um formato válido
      setDataDevolucao(inputDate);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (!dataDevolucao) {
        setErrorMessage('A data de devolução é obrigatória.');
        setShowErrorModal(true);
        return;
      }

      const dateRegex = /^\d{2}\/\d{2}\/\d{4}$/;
      if (!dateRegex.test(dataDevolucao)) {
        setErrorMessage('Formato de data de devolução inválido. Use o formato "dd/MM/yyyy".');
        setShowErrorModal(true);
        return;
      }

      // Formata a data de devolução após a validação
      const formattedDataDevolucao = format(new Date(dataDevolucao), 'dd/MM/yyyy');
      // FAZER O GET PRA ACHAR USUARIO , FAZER GET PRA ACHAR O RENT http://localhost:3001/v1/rent?readerId=id, FAZER O UPDATE DO RENT PRA FINISHED v1/rent/id, DELETAR
      const response = await axios.post('/seu-endpoint-de-post', {
        usuario,
        livro,
        dataDevolucao: formattedDataDevolucao,
      });

      if (response.status === 200) {
        setShowSuccessModal(true);
      }
    } catch (error) {
      console.error('Erro ao enviar POST request:', error);
      setErrorMessage('Erro ao registrar a devolução do livro. Por favor, tente novamente.');
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

  const [usuariosSugeridos, setUsuariosSugeridos] = useState([]);

  useEffect(() => {
    // Busque a lista de usuarios do seu sistema a partir de uma API ou outra fonte de dados em tempo real
    async function fetchUsuarios() {
      try {
        const response = await axios.get('/seu-endpoint-de-usuarios');
        const usuariosDoSistema = response.data;
        setUsuariosSugeridos(usuariosDoSistema);
      } catch (error) {
        console.error('Erro ao buscar a lista de usuarios:', error);
      }
    }

    fetchUsuarios();
  }, []);

  const handleUsuarioChange = (e) => {
    const inputUsuario = e.target.value;
    setUsuario(inputUsuario);
  }

  return (
    <div className={styles.container}>
      <h1>Devolução de Livro</h1>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Nome do Usuário" value={usuario} onChange={handleUsuarioChange} />
        <ul>
        {usuariosSugeridos.map((sugestao, index) => (
          <li key={index} onClick={() => setUsuario(sugestao)}>
            {sugestao}
          </li>
        ))}
      </ul>
        <input type="text" placeholder="Nome do Livro" value={livro} onChange={(e) => setLivro(e.target.value)} />
        <input type="text" placeholder="Data de Devolução (dd/MM/yyyy)" value={dataDevolucao} onChange={handleDataDevolucaoChange} />
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
