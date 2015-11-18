inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Skip processing of this recipe if it is not explicitly specified as the
# PREFERRED_PROVIDER for virtual/kernel. This avoids network access required
# by the use of AUTOREV SRCREVs, which are the default for this recipe.
python () {
    if d.getVar("PREFERRED_PROVIDER_virtual/kernel", True) != "linux-caros-dev":
        raise bb.parse.SkipPackage("Set PREFERRED_PROVIDER_virtual/kernel to linux-caros-dev to enable it")
}

RCONFLICTS_${PN} = "linux-tplino"
RREPLACES_${PN} = "linux-tplino"

KBRANCH ?= "standard/base"
KMETA = "kernel-meta"

SRC_URI = "git:///usr/src/tplino/fourfour/caros-kernel-dev/caros-linux-4.3;branch=${KBRANCH};name=machine \
           git:///usr/src/tplino/fourfour/caros-kernel-dev/yocto-kernel-cache;type=kmeta;name=meta;branch=master;destsuffix=${KMETA}"
SRC_URI += "file://caros-patches.scc"

# Set default SRCREVs. Both the machine and meta SRCREVs are statically set
# to the korg v3.7 tag, and hence prevent network access during parsing. If
# linux-yocto-dev is the preferred provider, they will be overridden to
# AUTOREV in following anonymous python routine and resolved when the
# variables are finalized.
SRCREV_machine ?= '${@oe.utils.conditional("PREFERRED_PROVIDER_virtual/kernel", "linux-caros-dev", "${AUTOREV}", "26029fe9e4ea17530f004f2d219038541f90e704", d)}'
SRCREV_meta ?= '${@oe.utils.conditional("PREFERRED_PROVIDER_virtual/kernel", "linux-caros-dev", "${AUTOREV}", "26029fe9e4ea17530f004f2d219038541f90e704", d)}'

LINUX_VERSION ?= "4.3"
LINUX_VERSION_EXTENSION = "-caros-${LINUX_KERNEL_TYPE}"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "(qemuarm|qemux86|qemuppc|qemumips|qemumips64|qemux86-64)"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc features/taskstats/taskstats.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append_qemux86=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append_qemux86-64=" cfg/sound.scc"
KERNEL_FEATURES_append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "" ,d)}"

# Install a relative symlink instead of an absolute one to support btrfs layout in grub
pkg_postinst_kernel-image () {
	update-alternatives --install /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ${KERNEL_IMAGETYPE} ${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ${KERNEL_PRIORITY} || true
}
