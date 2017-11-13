<?php 
    include "server.php";

    echo "<table>";
    
    $sql = selectAll();
    $respostas = doQuery($sql);

    if ($respostas->num_rows > 0) {
        // output data of each row
        while($row = $respostas->fetch_assoc()) {
            echo "<tr><td>".$row['tipo']."</td><td>".$row['nome']."</td></tr>";
            
        }
    } else {
        echo "0 results";
    }
    echo "</table>"
?>