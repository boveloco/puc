<?php     
    require 'server.php';
    
    session_start();
    
    const carro = "carro";
    const nome = "nome";
    const fvl = "fvl";
    const cep = "cep";
    const animal = "animal";
    const cor = "cor";


    $coisas = array();
    $coisas[carro]  = $_GET[carro];
    $coisas[nome]   = $_GET[nome];
    $coisas[fvl]    = $_GET[fvl];
    $coisas[cep]    = $_GET[cep];
    $coisas[animal] = $_GET[animal];
    $coisas[cor]    = $_GET[cor];

    $coisas[carro]  = verifyItem($coisas[carro], carro);
    $coisas[nome]   = verifyItem($coisas[nome], nome);
    $coisas[fvl]    = verifyItem($coisas[fvl], fvl);
    $coisas[cep]    = verifyItem($coisas[cep], cep);
    $coisas[animal] = verifyItem($coisas[animal], animal);
    $coisas[cor]    = verifyItem($coisas[cor], cor);

    echo "<table>";
    echo "<tr>";
    echo "<td>Carro</td><td>Nome</td><td>Fvl</td><td>CEP</td><td>Animal</td><td>Cor</td>";
    echo "</tr>";
    echo "<tr>";
    echo "<td>".$coisas[carro]."</td>"."<td>".$coisas[nome]."</td>"."<td>".$coisas[fvl]."</td>"."<td>".$coisas[cep]."</td>"."<td>".$coisas[animal]."</td>"."<td>".$coisas[cor]."</td>";
    echo "</tr>";
    echo "</table>";
    ?>
