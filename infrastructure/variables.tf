variable "product" {
    type    = "string"
    default = "em"
}

variable "location" {
    type    = "string"
    default = "UK South"
}

variable "env" {
    type = "string"
}

variable "infrastructure_env" {
    default     = "dev"
    description = "Infrastructure environment to point to"
}
