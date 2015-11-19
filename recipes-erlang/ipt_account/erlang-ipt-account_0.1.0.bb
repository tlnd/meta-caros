SUMMARY = "ipt_account interface for Erlang"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://MIT-LICENSE.txt;md5=2ad5b88aa2be935914ce2f735597aa5f"

PR = "r0.2"

SRC_URI = "git://github.com/RoadRunnr/ipt_account.git"
SRCREV = "${AUTOREV}"

DEPENDS = "erlang-lager erlang-gen-socket"

S = "${WORKDIR}/git"

TETRAPAK_OPTS += "-o build.version ${PV}-${PR}"

inherit tetrapak

python () {
    erlang_def_package("ipt-account", "ipt_account-*", "ebin", "include src NEWS.md README.md MIT-LICENSE.txt", d)
}
