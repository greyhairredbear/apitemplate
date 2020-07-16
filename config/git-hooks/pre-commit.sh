echo "Running detekt static analysis"

./gradlew preCommit
EXIT_CODE=$?

if [ $EXIT_CODE -ne 0 ]; then
  echo 1>&2 "*******************************************************"
  echo 1>&2 "                    Detekt failed                      "
  echo 1>&2 "     Please fix the above issues before committing     "
  echo 1>&2 "*******************************************************"
  exit $EXIT_CODE
else
  echo "Static analysis found no issues, proceeding with commit"
fi
