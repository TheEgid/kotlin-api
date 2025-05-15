$start = Get-Date

$scriptPath = $MyInvocation.MyCommand.Path
$scriptDir = Split-Path $scriptPath -Parent
$projectRoot = Split-Path $scriptDir -Parent
$projectName = Split-Path $projectRoot -Leaf

$src = "$projectRoot\"
$dst = [Environment]::GetFolderPath("Desktop") + "\" + $projectName + "\"

$excludeDirs = @(
    ".repomix",
    ".git",
    "my_server\app\build",
    "my_server\.gradle",
    ".gradle",
    "my_server\.kotlin",
    "my_server\bin",
    "my_server\build"
)

$excludeArgs = $excludeDirs | ForEach-Object { "/XD", "$src$_" }

robocopy $src $dst /MT:12 /MIR /XA:SH $excludeArgs /XJD /NFL /NDL

$elapsed = (Get-Date) - $start
Write-Output "$dst"
Write-Output "Script execution time: $($elapsed.TotalSeconds) seconds"
