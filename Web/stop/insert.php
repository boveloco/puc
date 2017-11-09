<?php 
    include "server.php";

    if(!isset($_GET['nome']) && !isset($_GET['tipo'])){
        return alert('Insira nome e tipo!');
    }

    $nome = $_GET['nome'];
    $tipo = $_GET['tipo'];

    $sql = prepareInsert($nome, $tipo);
    doQuery($sql);

    return alert($nome.' and '.$tipo.' inseridos');
?>