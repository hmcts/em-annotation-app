module "em-annotation-app" {
  source   = "git@github.com:contino/moj-module-webapp?ref=master"
  product  = "${var.product}-em-annotation-app"
  location = "${var.location}"
  env      = "${var.env}"
  ilbIp    = "${var.ilbIp}"

  app_settings = {
    POSTGRES_HOST     = "${module.em-annotation-postgres-db.host_name}"
    POSTGRES_PORT     = "${module.em-annotation-postgres-db.postgresql_listen_port}"
    POSTGRES_DATABASE = "${module.em-annotation-postgres-db.postgresql_database}"
    POSTGRES_USER     = "${module.em-annotation-postgres-db.user_name}"
    POSTGRES_PASSWORD = "${module.em-annotation-postgres-db.postgresql_password}"

    //    logging vars
    REFORM_TEAM = "${var.product}"
    REFORM_SERVICE_NAME = "em-annotation-app"
    REFORM_ENVIRONMENT = "${var.env}"

    SERVER_PORT = "8080"

    //
    SPRING_DATASOURCE_URL = "jdbc:postgresql://${module.em-annotation-postgres-db.host_name}:${module.em-annotation-postgres-db.postgresql_listen_port}/${module.em-annotation-postgres-db.postgresql_database}"
    SPRING_DATASOURCE_USERNAME = "${module.em-annotation-postgres-db.user_name}"
    SPRING_DATASOURCE_PASSWORD = "${module.em-annotation-postgres-db.postgresql_password}"

    // idam
    IDAM_CLIENT_URL = "${var.idam-api-url}"
    PROVIDER_SERVICE_CLIENT_URL = "${var.s2s-url}"
  }
}


module "em-annotation-postgres-db" {
  source              = "git@github.com:contino/moj-module-postgres?ref=master"
  product             = "${var.product}-em-annotation-postgres-db"
  location            = "West Europe"
  env                 = "${var.env}"
  postgresql_user     = "rhubarbadmin"
}

module "key-vault" {
  source              = "git@github.com:contino/moj-module-key-vault?ref=master"
  product             = "${var.product}-em-annotation-app"
  env                 = "${var.env}"
  tenant_id           = "${var.tenant_id}"
  object_id           = "${var.jenkins_AAD_objectId}"
  resource_group_name = "${module.em-annotation-app.resource_group_name}"
}

resource "azurerm_key_vault_secret" "POSTGRES-USER" {
  name      = "recipe-backend-POSTGRES-USER"
  value     = "${module.em-annotation-postgres-db.user_name}"
  vault_uri = "${module.key-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES-PASS" {
  name      = "recipe-backend-POSTGRES-PASS"
  value     = "${module.em-annotation-postgres-db.postgresql_password}"
  vault_uri = "${module.key-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES_HOST" {
  name      = "recipe-backend-POSTGRES-HOST"
  value     = "${module.em-annotation-postgres-db.host_name}"
  vault_uri = "${module.key-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES_PORT" {
  name      = "recipe-backend-POSTGRES-PORT"
  value     = "${module.em-annotation-postgres-db.postgresql_listen_port}"
  vault_uri = "${module.key-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES_DATABASE" {
  name      = "recipe-backend-POSTGRES-DATABASE"
  value     = "${module.em-annotation-postgres-db.postgresql_database}"
  vault_uri = "${module.key-vault.key_vault_uri}"
}
