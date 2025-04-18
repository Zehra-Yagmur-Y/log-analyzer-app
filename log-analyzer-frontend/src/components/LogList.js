import React, { useEffect, useState } from "react";
import axios from "axios";
import { Table, Container, Form, Button } from "react-bootstrap";

const LogList = () => {
  const [logs, setLogs] = useState([]);
  const [filteredLogs, setFilteredLogs] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedLevel, setSelectedLevel] = useState("");
  const [selectedDate, setSelectedDate] = useState("");

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/logs", {
        headers: { 'Accept': 'application/json' },
        timeout: 5000
      })
      .then((response) => {
        console.log("Gelen loglar:", response.data);
        setLogs(response.data);
        setFilteredLogs(response.data);
      })
      .catch((error) => {
        console.error("Logları çekerken hata oluştu:", error.message);
      });
  }, []);

  const handleFilter = () => {
    let updatedLogs = logs;

    if (selectedLevel) {
      updatedLogs = updatedLogs.filter((log) =>
        log.includes(`[${selectedLevel}]`)
      );
    }

    if (selectedDate) {
      updatedLogs = updatedLogs.filter((log) =>
        log.includes(selectedDate)
      );
    }

    if (searchTerm) {
      updatedLogs = updatedLogs.filter((log) =>
        log.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    setFilteredLogs(updatedLogs);
  };

  return (
    <Container className="mt-4">
      <h2 className="text-center"> Log Kayıtları</h2>

      {/* Filtreleme Alanı */}
      <Form className="mb-3">
        <Form.Group controlId="logLevel">
          <Form.Label>Log Seviyesi:</Form.Label>
          <Form.Select value={selectedLevel} onChange={(e) => setSelectedLevel(e.target.value)}>
            <option value="">Tümü</option>
            <option value="INFO">INFO</option>
            <option value="DEBUG">DEBUG</option>
            <option value="ERROR">ERROR</option>
          </Form.Select>
        </Form.Group>

        <Form.Group controlId="logDate">
          <Form.Label>Tarih:</Form.Label>
          <Form.Control
            type="text"
            placeholder="YYYY-MM-DD"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="search">
          <Form.Label>Arama:</Form.Label>
          <Form.Control
            type="text"
            placeholder="Anahtar kelime gir..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </Form.Group>

        <Button variant="primary" className="mt-2" onClick={handleFilter}>
          Filtrele
        </Button>
      </Form>

      {/* Log Tablosu */}
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Seviye</th>
            <th>Mesaj</th>
            <th>Tarih</th>
          </tr>
        </thead>
        <tbody>
          {filteredLogs.map((log, index) => {
            const logParts = log.match(/\[(.*?)\] (.*?) - (.*)/);
            const level = logParts ? logParts[1] : "UNKNOWN";
            const timestamp = logParts ? logParts[2] : "UNKNOWN";
            const message = logParts ? logParts[3] : log;

            return (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{level}</td>
                <td>{message}</td>
                <td>{timestamp}</td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </Container>
  );
};

export default LogList;
