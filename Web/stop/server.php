<?php
$servername = "localhost";
$username = "teste";
$password = "teste";
$dbname = "andrealissonrodrigo";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

function prepareSelect($name, $tipo){
    return "SELECT * from respostas where tipo = '".$tipo."' and nome = '".$name."'";
}

$sql = prepareSelect('jaca', 'comida');

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "<table><tr><th>ID</th><th>Name</th><th>Tipo</th></tr>";
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo "<tr><td>".$row["id"]."</td><td>".$row["nome"]."</td><td> ".$row["tipo"]."</td></tr>";
    }
    echo "</table>";
} else {
    echo "0 results";
}

$conn->close();
?>