<?php
    global $servername;
    global $username;
    global $password;
    global $dbname;
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "andrealissonrodrigo";

    // Create connection
    global $conn;

    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    } 

    function selectAll(){
        return "SELECT * FROM respostas ORDER BY tipo";
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
        global $servername;
        global $username;
        global $password;
        global $dbname;
        $conn = new mysqli($servername, $username, $password, $dbname); 
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        return $conn->query($query);
    }

    function verifyItem($name, $tipo) {
        $sql = prepareSelect($name, $tipo);
        $res = doQuery($sql);
        if ($res->num_rows > 0) {
            return "Acertou!";
        } else {
            return "Errou!";
        }    
    }

    $conn->close();
?>
