<?php 
    include "server.php";

    echo "<table>";
    
    $sql = selectAll();
    $respostas = doQuery($sql);

    if ($respostas->num_rows > 0) {
        while($row = $respostas->fetch_assoc()) {
            echo "<tr style='border: 1px'><td>".$row['tipo']."</td><td>".$row['nome']."</td></tr>";
            
        }
    } else {
        echo "0 results";
    }
    echo "</table>"
?>
