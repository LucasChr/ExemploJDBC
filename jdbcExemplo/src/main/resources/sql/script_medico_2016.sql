-- -----------------------------------------------------
-- Table Endereco
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Endereco (
  codendereco INT NOT NULL AUTO_INCREMENT,
  rua VARCHAR(45) NULL,
  bairro VARCHAR(45) NULL,
  PRIMARY KEY (codendereco))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Medico
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Medico (
  codmedico INT NOT NULL AUTO_INCREMENT,
  nommedico VARCHAR(45) NULL,
  crm VARCHAR(45) NULL,
  codendereco INT NOT NULL,
  PRIMARY KEY (codmedico),
  INDEX fk_Medico_Endereco (codendereco ASC),
  UNIQUE INDEX crm_UNIQUE (crm ASC),
  CONSTRAINT fk_Medico_Endereco
    FOREIGN KEY (codendereco)
    REFERENCES Endereco (codendereco)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Paciente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Paciente (
  codpaciente INT NOT NULL AUTO_INCREMENT,
  nompaciente VARCHAR(45) NULL,
  cpf VARCHAR(45) NULL,
  codendereco INT NOT NULL,
  PRIMARY KEY (codpaciente),
  INDEX fk_Paciente_Endereco1 (codendereco ASC),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC),
  CONSTRAINT fk_Paciente_Endereco1
    FOREIGN KEY (codendereco)
    REFERENCES Endereco (codendereco)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Consulta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Consulta (
  codconsulta INT NOT NULL AUTO_INCREMENT,
  data TIMESTAMP NULL,
  hora TIME NULL,
  codmedico INT NOT NULL,
  codpaciente INT NOT NULL,
  comissao FLOAT NULL,
  valorConsulta FLOAT NULL,
  PRIMARY KEY (codconsulta),
  INDEX fk_Consulta_Medico1 (codmedico ASC),
  INDEX fk_Consulta_Paciente1 (codpaciente ASC),
  CONSTRAINT fk_Consulta_Medico1
    FOREIGN KEY (codmedico)
    REFERENCES Medico (codmedico)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Consulta_Paciente1
    FOREIGN KEY (codpaciente)
    REFERENCES Paciente (codpaciente)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Medicamento
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Medicamento (
  codmedicamento INT NOT NULL AUTO_INCREMENT,
  nommedicamento VARCHAR(45) NULL,
  valorMedicamento FLOAT NULL,
  PRIMARY KEY (codmedicamento),
  UNIQUE INDEX nommedicamento_UNIQUE (nommedicamento ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Prescricao
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Prescricao (
  codprescricao INT NOT NULL AUTO_INCREMENT,
  codconsulta INT NOT NULL,
  codmedicamento INT NOT NULL,
  dosagem VARCHAR(45) NULL,
  INDEX fk_Consulta_has_Medicamento_Medicamento1 (codmedicamento ASC),
  INDEX fk_Consulta_has_Medicamento_Consulta1 (codconsulta ASC),
  PRIMARY KEY (codprescricao),
  CONSTRAINT fk_Consulta_has_Medicamento_Consulta1
    FOREIGN KEY (codconsulta)
    REFERENCES Consulta (codconsulta)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Consulta_has_Medicamento_Medicamento1
    FOREIGN KEY (codmedicamento)
    REFERENCES Medicamento (codmedicamento)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
