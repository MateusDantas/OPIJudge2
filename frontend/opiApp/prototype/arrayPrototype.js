/**
 * 
 */

Array.prototype.contains = function (object) {
	for (i in this) {
		if (this[i] == object)
			return true;
	}
	return false;
}