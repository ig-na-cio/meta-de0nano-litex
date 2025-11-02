SUMMARY = "Minimal test image"
LICENSE = "MIT"

inherit core-image
# inherit image

COMPATIBLE_MACHINE = "de0nano-litex"

# SÓLO busybox
IMAGE_INSTALL = "busybox"
# PACKAGE_INSTALL = "busybox"

# Formato filesystem
IMAGE_FSTYPES = "cpio.gz"

# Sin features
IMAGE_FEATURES = ""
EXTRA_IMAGE_FEATURES = ""

# Sin lenguajes extra
IMAGE_LINGUAS = ""

# Tamaño mínimo del filesystem
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_ROOTFS_MAXSIZE = "65536"

# Sin recomendaciones automáticas
BAD_RECOMMENDATIONS = "*"

IMAGE_FEATURES:remove = "package-management"