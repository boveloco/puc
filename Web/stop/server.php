<?php
$servername = "localhost";
$username = "teste";
$password = "teste";
$dbname = "andrealissonrodrigo";

// Create connection
global $conn;

$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

function prepareSelect($name, $tipo){
    return "SELECT * from respostas where tipo = '".$tipo."' and nome = '".$name."'";
}

function prepareInsert($name, $tipo){
    return "INSERT INTO respostas(nome, tipo) VALUES ('".$name."', '".$tipo."')"; 
}

function prepareDelete($name, $tipo){
    return "DELETE FROM respostas WHERE nome ='".$name."' AND tipo ='".$tipo."'";
}

function doQuery($query){
    global $conn;
    return $conn->query($query);    
}

function verifyItem($name, $tipo) {
    $sql = prepareSelect($name, $tipo);
    $result = doQuery($sql);
    if ($result->num_rows > 0) {
        return true;
    } else {
        return false;
    }    
}

# TESTES
#$sql = prepareInsert('teste','teste');
#$sql = prepareDelete('teste', 'teste');
#doQuery($sql);
#verifyItem('jaca', 'commida');
# - Testes
$conn->close();
/*
echo "<table><tr><th>ID</th><th>Name</th><th>Tipo</th></tr>";
// output data of each row
while($row = $result->fetch_assoc()) {
    echo "<tr><td>".$row["id"]."</td><td>".$row["nome"]."</td><td> ".$row["tipo"]."</td></tr>";
}
echo "</table>";
*/
?>