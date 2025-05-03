$start = Get-Date

$scriptPath = $script:MyInvocation.MyCommand.Path
$scriptParentDirectory = Split-Path $scriptPath -Parent
$scriptGrandParentDirectory = Split-Path $scriptParentDirectory -Parent
$folderName = Split-Path $scriptGrandParentDirectory -Leaf

$src = $scriptGrandParentDirectory + "\"
$dst = [Environment]::GetFolderPath("Desktop") + "\" + $folderName + "\"

$esx1 = $src + ".repomix"
$esx2 = $src + ".git"
$esx3 = $src + "app\build"
$esx4 = $src + ".gradle"
$esx5 = $src + ".kotlin"

robocopy $src $dst /MT:12 /MIR /XA:SH /XD $esx1 /XD $esx2 /XD $esx3 /XD $esx4 /XD $esx5 /XJD /NFL /NDL

# $entrypointSh = $dst + "postgres-db\docker-entrypoint.sh"
# (Get-Content $entrypointSh -Raw).Replace("`r`n", "`n") | Set-Content $entrypointSh -Force

$end = Get-Date
$elapsed = $end - $start
Write-Output $dst, "Script execution time: $($elapsed.TotalSeconds) seconds"
