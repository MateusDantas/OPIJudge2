<?hh

require_once('../config/connectionManager.php');

class Database {

  private static $link;
  public function __construct() {
    
    self::$link = $db_connection_manager->getLink();
  }

}