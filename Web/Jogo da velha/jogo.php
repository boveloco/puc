<script type="text/javascript" src="functions.js"></script>
<?php
session_start();

if(!isset($_SESSION['construtor'])){
    $_SESSION['matriz'] = array(array());
    $_SESSION['matriz'][0][0]= '-';
    $_SESSION['matriz'][1][1]= '-';
    $_SESSION['matriz'][2][2]= '-';
    $_SESSION['matriz'][1][0]= '-';
    $_SESSION['matriz'][2][0]= '-';
    $_SESSION['matriz'][0][1]= '-';
    $_SESSION['matriz'][0][2]= '-';
    $_SESSION['matriz'][1][2]= '-';
    $_SESSION['matriz'][2][1]= '-';

    $_SESSION['construtor'] = true;
    srand(time(NULL));
    $_SESSION['jogador'] = rand() % 2;
    $_SESSION['result'] = 0;
}

// toggle jogador, muda de jogador em cada rodada

function getValue(){
    if($_SESSION['jogador'] == 0){
        return 'O';
    } else {
        return 'X';
    }
} 

function isEmpty($i, $j){
    return  $_SESSION['matriz'][$i][$j] == '-';
}

function verifyVerticalEndHorizontal(){
    $p1 = 0;
    $p2 = 0;

    for($i = 0; $i < 3; $i++){
        $p1 = 0;
        $p2 = 0;
        for($j = 0; $j < 3; $j++){
            if($_SESSION['matriz'][$i][$j] == 'X'){
                $p1++;
                if($p1 == 3){
                    echo " Player1 e o vecedor!!!";
                    return 1;
                }
            }
            else if($_SESSION['matriz'][$i][$j] == 'O'){
                $p2++;
                if($p2 == 3){
                    echo " Player2 e o vecedor!!!";
                    return 1;
                }
            }
        }

        $p1 = 0;
        $p2 = 0;
        for($j = 0; $j < 3; $j++){
            if($_SESSION['matriz'][$j][$i] == 'X'){
                $p1++;
                if($p1 == 3){
                    echo " Player1 e o vecedor!!!";
                    return 1;
                }
            }
            else if($_SESSION['matriz'][$j][$i] == 'O'){
                $p2++;
                if($p2 == 3){
                    echo " Player2 e o vecedor!!!";
                    return 1;
                }
            }
        }
    }

    return 0;
}

function verifyDiagonal(){
    $p1 = 0;
    $p2 = 0;

    for($i = 0; $i < 3; $i++){
        if($_SESSION['matriz'][$i][$i] == 'X')
            $p1++;
        if($_SESSION['matriz'][$i][$i] == 'O')
            $p2++;
    }

    if($p1 == 3){
        echo " Player1 e o vecedor!!!";
        return 1;
    }
    if($p2 == 3){
        echo " Player2 e o vecedor!!!";
        return 1;
    }
    
    $p1 = 0;
    $p2 = 0;

    for($i = 0, $j = 2; $i < 3; $i++, $j--){
        if($_SESSION['matriz'][$i][$j] == 'X')
            $p1++;
        if($_SESSION['matriz'][$i][$j] == 'O')
            $p2++;
    }

    if($p1 == 3){
        echo " Player1 e o vecedor!!!";
        return 1;
    }
    if($p2 == 3){
        echo " Player2 e o vecedor!!!";
        return 1;
    }

    return 0;
}

function Veia(){
    $v = 0;
    for($i = 0; $i < 3; $i++){
        for($j = 0; $j < 3; $j++){
            if($_SESSION['matriz'][$i][$j] == 'X' ||
               $_SESSION['matriz'][$i][$j] == 'O'){
                $v++;
            }
        }
    }

    if($v == 9) {
        echo "Deu Veia!!!";
        return 1;
    }

    return 0;
}

function gameResult(){

    return (verifyVerticalEndHorizontal() + verifyDiagonal() < 1)? Veia() :  1;
}

if(isset($_GET['jogada']) && $_SESSION['result'] == 0){
    $jogada = $_GET['jogada'];
    $one = (int)substr($jogada, 0,1) -1;
    $two = (int)substr($jogada, 1) -1;

    echo $one;
    echo $two;
    if(isEmpty($one, $two)){
        $_SESSION['matriz'][$one][$two] = getValue();

        if($_SESSION['jogador'] == 0){
            $_SESSION['jogador'] = 1;
        } else {
            $_SESSION['jogador'] = 0;
        }
    }
}

$_SESSION['result'] = gameResult();

echo '<table>';
for($i = 1; $i < 4; $i++){
    echo '<tr>';
    for($j = 1; $j < 4; $j++){
        echo '<td>'
        .'<button onClick="marcar('.$i.$j.','.$_SESSION['jogador'].')">'.$_SESSION['matriz'][$i-1][$j-1].'</button>' 
        .'</td>';
    }
    echo '</tr>';
}
echo '</table>';
echo "<button id='delete' onClick = endGame()>Finalizar jogo</button>";

if(isset($_GET['delete'])){
    session_destroy();
}
?>
