<?php 
    include "server.php";

    session_start();
    
    if(!isset($_GET['nome']) || !isset($_GET['tipo']) || $_GET['nome'] == "" || $_GET['tipo'] == "") {
        echo "<script>alert('Escreva algo!')</script>";
        exit();
        return;
    }

    $nome = $_GET['nome'];
    $tipo = $_GET['tipo'];

    $sql = prepareDelete($nome, $tipo);
    $ret = doQuery($sql);

    echo "<script>alert('dados Removidos')</script>";
    echo "<a href=\"delete.html\"><button>Voltar</button></a>"
?>