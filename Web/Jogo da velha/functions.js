function marcar(num, jogador){
    document.location.href = 'http://localhost:8080/jogo.php?jogada=' + num +'&jogador=', jogador;
    
}

function endGame(){
    document.location.href = 'http://localhost:8080/jogo.php?delete=true';
}