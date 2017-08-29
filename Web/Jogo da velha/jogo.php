<script type="text/javascript" src="functions.js"></script>
<?php
session_start();

if(!isset($_SESSION['construtor'])){
    $_SESSION['matriz'] = array(array());
    $_SESSION['matriz'][0][0]= 'O';
    $_SESSION['matriz'][1][1]= 'O';
    $_SESSION['matriz'][2][2]= 'O';
    $_SESSION['matriz'][1][0]= 'O';
    $_SESSION['matriz'][2][0]= 'O';
    $_SESSION['matriz'][0][1]= 'O';
    $_SESSION['matriz'][0][2]= 'O';
    $_SESSION['matriz'][1][2]= 'O';
    $_SESSION['matriz'][2][1]= 'O';

    $_SESSION['construtor'] = true;
    $_SESSION['jogador'] = 0;
}

// toggle jogador, muda de jogador em cada rodada
if($_SESSION['jogador'] == 0){
    $_SESSION['jogador'] = 1;
} else {
    $_SESSION['jogador'] = 0;
}

function getValue(){
    if($_SESSION['jogador'] == 0){
        return 'O';
    } else {
        return 'X';
    }
} 

if(isset($_GET['jogada'])){
    $jogada = $_GET['jogada'];
    $one = (int)substr($jogada, 0,1) -1;
    $two = (int)substr($jogada, 1) -1;

    echo $one;
    echo $two;
    $_SESSION['matriz'][$one][$two] = getValue();
}

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
echo "<button id='delete' onClick(endGame())>Finalizar jogo</button>";

if(isset($_GET['delete'])){
    session_destroy();
}
?>
