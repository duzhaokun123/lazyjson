rootProject.name = "lazyjson"
include("codegen")
include("annotation")
include("commandline")
include("retrofit2-converter")
if (file("ijpg").exists()) {
    include("intellij-plugin")
}

