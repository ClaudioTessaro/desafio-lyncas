CREATE TABLE Organizacao
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    nome       VARCHAR(100)          NOT NULL,
    email      VARCHAR(100)          NOT NULL,
    password   VARCHAR(100)          NOT NULL,
    descricao  TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Contas_Pagar
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    data_vencimento DATE                  NOT NULL,
    data_pagamento  DATE,
    valor           DECIMAL(10, 2)        NOT NULL,
    descricao       TEXT,
    situacao        VARCHAR(100)          NOT NULL,
    organizacao_id  BIGINT                NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organizacao_id) REFERENCES Organizacao (id)
);
)