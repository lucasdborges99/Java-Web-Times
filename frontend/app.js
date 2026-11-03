const API_URL = 'http://localhost:8080/times';

const formTime = document.getElementById('formulario-time');
const inputId = document.getElementById('time-id');
const inputNome = document.getElementById('nome');
const inputPais = document.getElementById('pais');
const inputTitulos = document.getElementById('titulos');
const inputEstadio = document.getElementById('estadio');
const btnSalvar = document.getElementById('btn-salvar');
const btnCancelar = document.getElementById('btn-cancelar');
const tituloForm = document.getElementById('titulo-formulario');

const inputBusca = document.getElementById('busca-nome');
const btnBuscar = document.getElementById('btn-buscar');
const tabelaCorpo = document.getElementById('tabela-corpo');
const containerAlerta = document.getElementById('container-alerta');

formTime.addEventListener('submit', salvarTime);
btnBuscar.addEventListener('click', () => buscarTime(inputBusca.value));
btnCancelar.addEventListener('click', limparForm);

function mostrarAlerta(mensagem, tipo = 'sucesso') {
    let classeAlerta = 'alerta-sucesso';
    if (tipo === 'aviso' || tipo === 'warning') {
        classeAlerta = 'alerta-aviso';
    } else if (tipo === 'erro' || tipo === 'danger') {
        classeAlerta = 'alerta-erro';
    }

    containerAlerta.innerHTML = `
        <div class="alerta ${classeAlerta}">
            <span>${mensagem}</span>
            <button class="btn-fechar" onclick="fecharAlerta()">&times;</button>
        </div>
    `;
    setTimeout(fecharAlerta, 4000);
}
function fecharAlerta() {
    containerAlerta.innerHTML = '';
}
async function buscarTime(nome) {
    if (!nome.trim()) {
        mostrarAlerta('Digite o nome do time para pesquisar.', 'aviso');
        return;
    }
    try {
        const res = await fetch(`${API_URL}?nome=${encodeURIComponent(nome)}`);
        if (!res.ok) {
            if (res.status === 404 || res.status === 500) {
                throw new Error('Time não encontrado!');
            }
            throw new Error('Erro ao buscar o time.');
        }

        const time = await res.json();
        renderizarTabela(time);
        mostrarAlerta(`Time "${time.nome}" localizado com sucesso!`, 'sucesso');
    } catch (erro) {
        tabelaCorpo.innerHTML = `<tr><td colspan="6" class="texto-erro">${erro.message}</td></tr>`;
        mostrarAlerta(erro.message, 'erro');
    }
}

function renderizarTabela(time) {
    tabelaCorpo.innerHTML = `
        <tr>
            <td>${time.id}</td>
            <td class="nome-destaque">${time.nome}</td>
            <td>${time.pais}</td>
            <td>🏆 ${time.titulos}</td>
            <td>${time.estadio}</td>
            <td>
                <button class="btn-acao btn-editar" onclick="prepararEdicao(${time.id}, '${time.nome}', '${time.pais}', ${time.titulos}, '${time.estadio}')">Editar</button>
                <button class="btn-acao btn-excluir" onclick="excluirTime('${time.nome}')">Excluir</button>
            </td>
        </tr>
    `;
}

async function salvarTime(e) {
    e.preventDefault();

    const id = inputId.value;
    const dados = {
        nome: inputNome.value,
        pais: inputPais.value,
        titulos: parseInt(inputTitulos.value),
        estadio: inputEstadio.value
    };

    try {
        let res;
        if (id) {
            res = await fetch(`${API_URL}?id=${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });
        } else {
            res = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });
        }

        if (!res.ok) {
            throw new Error('Erro ao processar requisição no servidor.');
        }

        mostrarAlerta(id ? 'Time atualizado com sucesso!' : 'Time cadastrado com sucesso!', 'sucesso');
        const nomeBuscado = dados.nome;
        limparForm();
        buscarTime(nomeBuscado);
    } catch (erro) {
        mostrarAlerta(erro.message, 'erro');
    }
}

async function excluirTime(nome) {
    if (!confirm(`Deseja excluir o time "${nome}"?`)) return;

    try {
        const res = await fetch(`${API_URL}?nome=${encodeURIComponent(nome)}`, {
            method: 'DELETE'
        });

        if (!res.ok) {
            throw new Error('Erro ao excluir o time.');
        }

        mostrarAlerta(`Time "${nome}" excluído com sucesso!`, 'sucesso');
        tabelaCorpo.innerHTML = `<tr><td colspan="6" class="texto-vazio">Time excluído. Faça uma nova busca.</td></tr>`;
        limparForm();
    } catch (erro) {
        mostrarAlerta(erro.message, 'erro');
    }
}

function prepararEdicao(id, nome, pais, titulos, estadio) {
    inputId.value = id;
    inputNome.value = nome;
    inputPais.value = pais;
    inputTitulos.value = titulos;
    inputEstadio.value = estadio;

    tituloForm.textContent = 'Editar Time';
    btnSalvar.textContent = 'Atualizar Time (PUT)';
    btnCancelar.classList.remove('oculto');
}


function limparForm() {
    inputId.value = '';
    formTime.reset();
    tituloForm.textContent = 'Cadastrar Novo Time';
    btnSalvar.textContent = 'Salvar Time';
    btnCancelar.classList.add('oculto');
}