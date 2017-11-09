<?php 
    include "server.php";

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
        
    
    $coisas[carro]  = verifyItem($_GET[carro], carro);
    $coisas[nome]   = verifyItem($_GET[nome]);
    $coisas[fvl]    = verifyItem($_GET[fvl]);
    $coisas[cep]    = verifyItem($_GET[cep]);
    $coisas[animal] = verifyItem($_GET[animal]);
    $coisas[cor]    = verifyItem($_GET[cor]);

    echo "<table>"
    echo "<tr>"
    echo "<td>Carro</td><td>Nome</td><td>Fvl</td><td>CEP</td><td>Animal</td><td>Cor</td>"
    echo "</tr>"
    echo "<tr>"
    echo "<td>".$coisas[carro]."</td>"."<td>".$coisas[nome]."</td>"."<td>".$coisas[fvl]."</td>"."<td>".$coisas[cep]."</td>"."<td>".$coisas[animal]."</td>"."<td>".$coisas[cor]."</td>".
    echo "</tr>"
    echo "</table>"
    // for ($i = 1; $i <= 5; $i++) {
    //     if(!isset($coisas[$i]) || $coisas[$i] == ""){
    //         $coisas[$i] = false;
    //         continue;
    //     } 
    //     switch ($i){
    //         case carro: 
    //             $coisas[$i] = verifyItem($coisas[$i], 'carro');
    //             break;
    //         case nome: 
    //             $coisas[$i] = verifyItem($coisas[$i], 'nome');
    //             break;
    //         case fvl:
    //             $coisas[$i] = verifyItem($coisas[$i], 'fvl');
    //             break;
    //         case cep:
    //             $coisas[$i] = verifyItem($coisas[$i], 'cep');
    //             break;
    //         case animal:
    //             $coisas[$i] = verifyItem($coisas[$i], 'animal');
    //             break;
    //         case cor
    //     }
    // }
    
    
    ?>