module "em-annotation-app" {
    source   = "git@github.com:contino/moj-module-webapp?ref=0.0.78"
    product  = "${var.product}-em-annotation-app"
    location = "${var.location}"
    env      = "${var.env}"
    asename  = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"

    app_settings = {
        POSTGRES_HOST     = "${module.em-annotation-postgres-db.host_name}"
        POSTGRES_PORT     = "${module.em-annotation-postgres-db.postgresql_listen_port}"
        POSTGRES_DATABASE = "${module.em-annotation-postgres-db.postgresql_database}"
        POSTGRES_USER     = "${module.em-annotation-postgres-db.user_name}"
    }
}


module "em-annotation-postgres-db" {
    source              = "git@github.com:contino/moj-module-postgres?ref=master"
    product             = "${var.product}-em-annotation-postgres-db"
    location            = "West Europe"
    env                 = "${var.env}"
    postgresql_user     = "rhubarbadmin"
    postgresql_password = "Password1"
}
