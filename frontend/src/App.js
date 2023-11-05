import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Note a mudança aqui, importe 'Routes' em vez de 'Switch'

import HomePage from './pages/HomePage';
import CadastroUsuarioPage from './pages/CadastroUsuarioPage';
import CadastroAutorPage from './pages/CadastroAutorPage';
import CadastroLivroPage from './pages/CadastroLivroPage';
import AluguelLivroPage from './pages/AluguelLivroPage';

function App() {
  return (
    <Router>
      <Routes> {/* Troque 'Switch' por 'Routes' */}
        <Route path="/" element={<HomePage />} />
        <Route path="/cadastro-usuario" element={<CadastroUsuarioPage />} />
        <Route path="/cadastro-autor" element={<CadastroAutorPage />} />
        <Route path="/cadastro-livro" element={<CadastroLivroPage />} />
        <Route path="/aluguel-livro" element={<AluguelLivroPage />} />
      </Routes>
    </Router>
  );
}

export default App;
