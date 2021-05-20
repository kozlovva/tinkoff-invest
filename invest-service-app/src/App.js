import './App.css';
import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import { PieChartView } from "./PieChartView";
import { calcChanges } from "./service/changesService";

const target = 200000.0;

const client = new Client({
  brokerURL: 'ws://localhost:8080/ws',
  debug: (str) => {
    console.log(str);
  },
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000,
});

client.activate();

function App() {

  const [profile, setProfile] = useState({
    freeMoney: {
      value: 0,
      changes: 0
    },
    activeMoney: {
      value: 0,
      changes: 0
    },
    total: {
      value: 0,
      changes: 0
    },
    date: ""
  });
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/balance")
      .then(res => res.json())
      .then(
        (result) => {
          setIsLoaded(true);
          setProfile(calcChanges(profile, result));
        },
        // Примечание: важно обрабатывать ошибки именно здесь, а не в блоке catch(),
        // чтобы не перехватывать исключения из ошибок в самих компонентах.
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }, [])

  client.onConnect = () => {
    console.debug("Connected");

    client.subscribe("/topic/profile", m => {
      var data = JSON.parse(m.body);
      console.info("Message", data);
      setProfile(calcChanges(profile, data));
    });

  }

  if (error)
    return <div>Ошибка: {error.message}</div>

  if (!isLoaded)
    return <div>Загрузка...</div>

  return (
    <div className="App">
      <header className="App-header">
        <div className="Chart">
          <PieChartView actual={profile.total.value} target={target} />
        </div>
        <div className="Info">
          <div>Свободные деньги: {profile.freeMoney.value} ₽ ({profile.freeMoney.changes})</div>
          <div>Деньги в активах: {profile.activeMoney.value} ₽ ({profile.activeMoney.changes})</div>
          <div>Всего: {profile.total.value} ₽ ({profile.total.changes})</div>
          <div>Цель: {target} ₽</div>
          <div>Дата последнего обновления: {profile.date}</div>
        </div>
      </header>
    </div>
  );
}

export default App;
